package gp

import com.thoughtworks.binding.Binding
import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.dom
import org.scalajs.dom.{Event, MouseEvent, console}
import org.scalajs.dom.html._
import org.scalajs.dom.raw.Node

import scala.scalajs.js.annotation.JSExportTopLevel
import scalatags.JsDom.TypedTag
import scalatags.JsDom.all._

import scala.scalajs.js


object Front {

  @JSExportTopLevel("binding.attach")
  def attach(parent : Node, child: Binding[Node]) = dom.render(parent, child)
}

object EditableInput {

  type MouseEventHandler = MouseEvent => Unit

  @JSExportTopLevel("gp.EditableInput")
  def binding(initialValue : String,
              onValidate : js.Function1[String, Boolean]
             ) : Binding[Div] = {
    new EditableInput(initialValue, onValidate.apply).binding
  }

}
import EditableInput._

class EditableInput
(//inputCssClass : List[String],
 initialValue : String,
 //onValidate :  String => Future[Boolean]
 onValidate :  String => Boolean
) {

  private [this] val inputValue = Var(initialValue)
  private [this] val editable = Var(false)

  private [this] var previousValue = initialValue

  val onChange : Event => Unit =
  { (evt : Event ) =>
    inputValue := evt.srcElement.asInstanceOf[Input].value
  }

  val toggleEditable : MouseEventHandler =
    (me : MouseEvent) => {
      editable := !editable.get
    }

  val validateHandler : MouseEventHandler =
    (me : MouseEvent) => {
      if(onValidate(inputValue.get))
        editable := !editable.get
    }

  private val toggleAndSave =
    toggleEditable andThen ( _ => previousValue = inputValue.get )
  private val toggleAndRestore =
    toggleEditable andThen (_ => inputValue := previousValue)

  def buttons(editable : Boolean) : TypedTag[Element] =
    if(! editable) button("Edit", `type`:="button", onclick := toggleAndSave)
    else {
      div(
        button("Ok", `type` := "button", onclick := validateHandler),
        button("Cancel", `type` := "button", onclick := toggleAndRestore)
      )
    }

  def renderer(editable : Boolean, inputValue: String) : TypedTag[Div] = {
    val disableAttr  : Modifier =
      if(!editable) disabled
      else ()

    div(
      input(`type` := "text", value := inputValue, //`class` := inputCssClass.mkString(" "),
        disableAttr,
        onchange := onChange),
      buttons(editable) )
  }

  def binding : Binding[Div] =
    Binding {
      renderer(editable.bind, inputValue.bind).render
    }


}