package abc.parser.def;

import scanner.AutomataDefinition;
import scanner.State;
import scanner.Scanner;
import scanner.Transition;
import scanner.IsDigitTransition;

import abc.parser.AbcTokenType;

/** **/
public class FieldElemskipDefinition extends AutomataDefinition
{
    public FieldElemskipDefinition()
    { buildDefinition(); }

    protected void buildDefinition()
    {
        State startingState = new State(AbcTokenType.UNKNOWN, false);
        Transition trans = new Transition(startingState,'E');
        getStartingState().addTransition(trans);
        //startingState.addTransition(new IsColonTransition(new State(AbcTokenType.FIELD_ELEMSKIP, true)));
    }

}

