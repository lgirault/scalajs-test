
const world = require('../target/scala-2.12/scalajs-tests-fastopt');

const root = document.getElementById('editable-input');

function alwaysTrue(input) {
    return true;
}

const ei = world.gp.EditableInput("toto", alwaysTrue);
world.binding.attach(root, ei);
