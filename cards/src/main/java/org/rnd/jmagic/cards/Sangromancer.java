package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Sangromancer")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE, SubType.SHAMAN})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class Sangromancer extends Card
{
	public static final class SangromancerAbility1 extends EventTriggeredAbility
	{
		public SangromancerAbility1(GameState state)
		{
			super(state, "Whenever a creature an opponent controls dies, you may gain 3 life.");
			this.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), true));
			this.addEffect(youMay(gainLife(You.instance(), 3, "You gain 3 life."), "You may gain 3 life."));
		}
	}

	public static final class SangromancerAbility2 extends EventTriggeredAbility
	{
		public SangromancerAbility2(GameState state)
		{
			super(state, "Whenever an opponent discards a card, you may gain 3 life.");
			SimpleEventPattern discard = new SimpleEventPattern(EventType.DISCARD_ONE_CARD);
			discard.put(EventType.Parameter.CARD, new OwnedByPattern(OpponentsOf.instance(You.instance())));
			this.addPattern(discard);
			this.addEffect(youMay(gainLife(You.instance(), 3, "You gain 3 life."), "You may gain 3 life."));
		}
	}

	public Sangromancer(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever a creature an opponent controls is put into a graveyard from
		// the battlefield, you may gain 3 life.
		this.addAbility(new SangromancerAbility1(state));

		// Whenever an opponent discards a card, you may gain 3 life.
		this.addAbility(new SangromancerAbility2(state));
	}
}
