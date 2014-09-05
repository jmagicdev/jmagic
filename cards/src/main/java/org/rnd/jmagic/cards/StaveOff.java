package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Stave Off")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class StaveOff extends Card
{
	public StaveOff(GameState state)
	{
		super(state);

		// Target creature gains protection from the color of your choice until
		// end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		EventFactory chooseColor = playerChoose(You.instance(), 1, Identity.fromCollection(Color.allColors()), PlayerInterface.ChoiceType.COLOR, PlayerInterface.ChooseReason.CHOOSE_COLOR, "");
		this.addEffect(chooseColor);

		SetGenerator color = EffectResult.instance(chooseColor);
		this.addEffect(addProtectionUntilEndOfTurn(target, color, "Target creature gains protection from the color of your choice until end of turn."));
	}
}
