package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Abattoir Ghoul")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class AbattoirGhoul extends Card
{
	public static final class AbattoirGhoulAbility1 extends EventTriggeredAbility
	{
		public AbattoirGhoulAbility1(GameState state)
		{
			super(state, "Whenever a creature dealt damage by Abattoir Ghoul this turn dies, you gain life equal to that creature's toughness.");

			SetGenerator thisCreature = ABILITY_SOURCE_OF_THIS;
			SetGenerator damagedByThis = DealtDamageByThisTurn.instance(thisCreature);
			this.addPattern(whenXDies(damagedByThis));

			SetGenerator creature = OldObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			SetGenerator amount = ToughnessOf.instance(creature);
			this.addEffect(gainLife(You.instance(), amount, "You gain life equal to that creature's toughness."));

			state.ensureTracker(new DealtDamageByThisTurn.DealtDamageByTracker());
		}
	}

	public AbattoirGhoul(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// Whenever a creature dealt damage by Abattoir Ghoul this turn dies,
		// you gain life equal to that creature's toughness.
		this.addAbility(new AbattoirGhoulAbility1(state));
	}
}
