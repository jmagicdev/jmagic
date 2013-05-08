package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cephalid Constable")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.CEPHALID})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.JUDGMENT, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class CephalidConstable extends Card
{
	public static final class Cephaliderang extends EventTriggeredAbility
	{
		public Cephaliderang(GameState state)
		{
			super(state, "Whenever Cephalid Constable deals combat damage to a player, return up to that many target permanents that player controls to their owners' hands.");

			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			SetGenerator assignments = TriggerDamage.instance(This.instance());
			SetGenerator controlledByDefender = ControlledBy.instance(TakerOfDamage.instance(assignments));
			Target target = this.addTarget(controlledByDefender, "up to that many target permanents that player controls");

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;
			SetGenerator totalDamageDealt = Count.instance(DamageDealtBy.instance(thisCard, assignments));
			target.setRange(Between.instance(numberGenerator(0), totalDamageDealt));

			this.addEffect(bounce(targetedBy(target), "Return up to that many target permanents that player controls to their owners' hands."));
		}
	}

	public CephalidConstable(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new Cephaliderang(state));
	}
}
