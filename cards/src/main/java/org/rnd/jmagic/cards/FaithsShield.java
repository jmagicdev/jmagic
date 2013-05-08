package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Faith's Shield")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class FaithsShield extends Card
{
	public FaithsShield(GameState state)
	{
		super(state);

		// Target permanent you control gains protection from the color of your
		// choice until end of turn. If you have 5 or less life, instead you and
		// each permanent you control gain protection from the color of your
		// choice until end of turn.
		SetGenerator permanentsYouControl = Intersect.instance(Permanents.instance(), ControlledBy.instance(You.instance()));
		SetGenerator target = targetedBy(this.addTarget(permanentsYouControl, "target permanent you control"));

		EventFactory chooseColor = playerChoose(You.instance(), 1, Identity.instance(Color.allColors()), PlayerInterface.ChoiceType.COLOR, PlayerInterface.ChooseReason.CHOOSE_COLOR, "");
		this.addEffect(chooseColor);

		SetGenerator color = EffectResult.instance(chooseColor);
		SetGenerator affected = IfThenElse.instance(FatefulHour.instance(), Union.instance(You.instance(), permanentsYouControl), target);

		this.addEffect(addProtectionUntilEndOfTurn(affected, color, "Target permanent you control gains protection from the color of your choice until end of turn. If you have 5 or less life, instead you and each permanent you control gain protection from the color of your choice until end of turn."));
	}
}
