package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cartel Aristocrat")
@Types({Type.CREATURE})
@SubTypes({SubType.ADVISOR, SubType.HUMAN})
@ManaCost("WB")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class CartelAristocrat extends Card
{
	public static final class CartelAristocratAbility0 extends ActivatedAbility
	{
		public CartelAristocratAbility0(GameState state)
		{
			super(state, "Sacrifice another creature: Cartel Aristocrat gains protection from the color of your choice until end of turn.");

			SetGenerator anotherCreature = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			this.addCost(sacrifice(You.instance(), 1, anotherCreature, "Sacrifice another creature"));

			EventFactory chooseColor = playerChoose(You.instance(), 1, Identity.instance(Color.allColors()), PlayerInterface.ChoiceType.COLOR, PlayerInterface.ChooseReason.CHOOSE_COLOR, "");
			this.addEffect(chooseColor);

			SetGenerator color = EffectResult.instance(chooseColor);
			this.addEffect(addProtectionUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, color, "Target creature gains protection from the color of your choice until end of turn."));
		}
	}

	public CartelAristocrat(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Sacrifice another creature: Cartel Aristocrat gains protection from
		// the color of your choice until end of turn.
		this.addAbility(new CartelAristocratAbility0(state));
	}
}
