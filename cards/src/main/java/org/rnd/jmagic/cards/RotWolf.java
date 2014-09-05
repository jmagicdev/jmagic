package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Rot Wolf")
@Types({Type.CREATURE})
@SubTypes({SubType.WOLF})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class RotWolf extends Card
{
	public static final class RotWolfAbility1 extends EventTriggeredAbility
	{
		public RotWolfAbility1(GameState state)
		{
			super(state, "Whenever a creature dealt damage by Rot Wolf this turn dies, you may draw a card.");

			SetGenerator damagedByThis = DealtDamageByThisTurn.instance(ABILITY_SOURCE_OF_THIS);

			this.addPattern(whenXDies(Intersect.instance(CreaturePermanents.instance(), damagedByThis)));

			this.addEffect(youMay(drawACard(), "You may draw a card."));

			state.ensureTracker(new DealtDamageByThisTurn.DealtDamageByTracker());
		}
	}

	public RotWolf(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// Whenever a creature dealt damage by Rot Wolf this turn is put into a
		// graveyard, you may draw a card.
		this.addAbility(new RotWolfAbility1(state));
	}
}
