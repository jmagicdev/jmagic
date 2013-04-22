package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.abilities.keywords.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Domri Rade")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.DOMRI})
@ManaCost("1RG")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class DomriRade extends Card
{
	public static final class DomriRadeAbility0 extends LoyaltyAbility
	{
		public DomriRadeAbility0(GameState state)
		{
			super(state, +1, "Look at the top card of your library. If it's a creature card, you may reveal it and put it into your hand.");

			EventFactory look = look(You.instance(), TopCards.instance(1, LibraryOf.instance(You.instance())), "Look at the top card of your library.");
			this.addEffect(look);

			SetGenerator revealed = EffectResult.instance(look);
			this.addEffect(ifThen(Intersect.instance(revealed, HasType.instance(Type.CREATURE)), ifThen(youMay(reveal(revealed, "Reveal it."), "You may reveal it."), putIntoHand(revealed, You.instance(), "Put it into your hand."), "You may reveal it and put it into your hand."), "If it's a creature card, you may reveal it and put it into your hand."));
		}
	}

	public static final class DomriRadeAbility1 extends LoyaltyAbility
	{
		public DomriRadeAbility1(GameState state)
		{
			super(state, -2, "Target creature you control fights another target creature.");

			Target firstTarget = this.addTarget(CREATURES_YOU_CONTROL, "target creature you control");
			firstTarget.restrictFromLaterTargets = true;

			Target secondTarget = this.addTarget(CreaturePermanents.instance(), "another target creature");

			this.addEffect(fight(Union.instance(targetedBy(firstTarget), targetedBy(secondTarget)), "Target creature you control fights another target creature."));
		}
	}

	public static final class DomriRadeAbility2 extends LoyaltyAbility
	{
		public static final class DomriPump extends StaticAbility
		{
			public DomriPump(GameState state)
			{
				super(state, "Creatures you control have double strike, trample, hexproof, and haste.");

				this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, DoubleStrike.class, Trample.class, Hexproof.class, Haste.class));
			}
		}

		public DomriRadeAbility2(GameState state)
		{
			super(state, -7, "You get an emblem with \"Creatures you control have double strike, trample, hexproof, and haste.\"");

			EventFactory makeEmblem = new EventFactory(EventType.CREATE_EMBLEM, "You get an emblem with \"Creatures you control have double strike, trample, hexproof, and haste.\"");
			makeEmblem.parameters.put(EventType.Parameter.CAUSE, This.instance());
			makeEmblem.parameters.put(EventType.Parameter.ABILITY, Identity.instance(DomriPump.class));
			makeEmblem.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			this.addEffect(makeEmblem);
		}
	}

	public DomriRade(GameState state)
	{
		super(state);

		this.setPrintedLoyalty((3));

		// +1: Look at the top card of your library. If it's a creature card,
		// you may reveal it and put it into your hand.
		this.addAbility(new DomriRadeAbility0(state));

		// -2: Target creature you control fights another target creature.
		this.addAbility(new DomriRadeAbility1(state));

		// -7: You get an emblem with
		// "Creatures you control have double strike, trample, hexproof, and haste."
		this.addAbility(new DomriRadeAbility2(state));
	}
}
