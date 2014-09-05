package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Garruk, the Veil-Cursed")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.GARRUK})
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class GarruktheVeilCursed extends AlternateCard
{
	public static final class GarruktheVeilCursedAbility0 extends LoyaltyAbility
	{
		public GarruktheVeilCursedAbility0(GameState state)
		{
			super(state, +1, "Put a 1/1 black Wolf creature token with deathtouch onto the battlefield.");

			CreateTokensFactory factory = new CreateTokensFactory(1, 1, 1, "Put a 1/1 black Wolf creature token with deathtouch onto the battlefield.");
			factory.setColors(Color.BLACK);
			factory.setSubTypes(SubType.WOLF);
			factory.addAbility(org.rnd.jmagic.abilities.keywords.Deathtouch.class);
			this.addEffect(factory.getEventFactory());
		}
	}

	public static final class GarruktheVeilCursedAbility1 extends LoyaltyAbility
	{
		public GarruktheVeilCursedAbility1(GameState state)
		{
			super(state, -1, "Sacrifice a creature. If you do, search your library for a creature card, reveal it, put it into your hand, then shuffle your library.");

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a creature card, reveal it, put it into your hand, then shuffle your library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.CREATURE)));

			this.addEffect(ifThen(sacrifice(You.instance(), 1, CreaturePermanents.instance(), "Sacrifice a creature."), search, "Sacrifice a creature. If you do, search your library for a creature card, reveal it, put it into your hand, then shuffle your library."));
		}
	}

	public static final class GarruktheVeilCursedAbility2 extends LoyaltyAbility
	{
		public GarruktheVeilCursedAbility2(GameState state)
		{
			super(state, -3, "Creatures you control gain trample and get +X/+X until end of turn, where X is the number of creature cards in your graveyard.");

			SetGenerator X = Count.instance(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))));

			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, X, X, "Creatures you control gain trample and get +X/+X until end of turn, where X is the number of creature cards in your graveyard.", org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public GarruktheVeilCursed(GameState state)
	{
		super(state);

		this.setColorIndicator(Color.BLACK, Color.GREEN);

		// +1: Put a 1/1 black Wolf creature token with deathtouch onto the
		// battlefield.
		this.addAbility(new GarruktheVeilCursedAbility0(state));

		// -1: Sacrifice a creature. If you do, search your library for a
		// creature card, reveal it, put it into your hand, then shuffle your
		// library.
		this.addAbility(new GarruktheVeilCursedAbility1(state));

		// -3: Creatures you control gain trample and get +X/+X until end of
		// turn, where X is the number of creature cards in your graveyard.
		this.addAbility(new GarruktheVeilCursedAbility2(state));
	}
}
