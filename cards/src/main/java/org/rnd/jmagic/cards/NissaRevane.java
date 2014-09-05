package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Nissa Revane")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.NISSA})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN})
public final class NissaRevane extends Card
{
	public static final class Search extends LoyaltyAbility
	{
		public Search(GameState state)
		{
			super(state, +1, "Search your library for a card named Nissa's Chosen and put it onto the battlefield. Then shuffle your library.");

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			parameters.put(EventType.Parameter.TO, Battlefield.instance());
			parameters.put(EventType.Parameter.TYPE, Identity.instance(HasName.instance("Nissa's Chosen")));
			this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, parameters, "Search your library for a card named Nissa's Chosen and put it onto the battlefield. Then shuffle your library."));
		}
	}

	public static final class DoubleWellwisher extends LoyaltyAbility
	{
		public DoubleWellwisher(GameState state)
		{
			super(state, +1, "You gain 2 life for each Elf you control.");

			SetGenerator elvesYouControl = Intersect.instance(HasSubType.instance(SubType.ELF), ControlledBy.instance(You.instance()));
			SetGenerator lifeAmount = Multiply.instance(numberGenerator(2), Count.instance(elvesYouControl));

			this.addEffect(gainLife(You.instance(), lifeAmount, "You gain 2 life for each Elf you control."));
		}
	}

	public static final class OmgElves extends LoyaltyAbility
	{
		public OmgElves(GameState state)
		{
			super(state, -7, "Search your library for any number of Elf creature cards and put them onto the battlefield. Then shuffle your library.");

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			parameters.put(EventType.Parameter.NUMBER, Identity.instance(new org.rnd.util.NumberRange(0, null)));
			parameters.put(EventType.Parameter.TO, Battlefield.instance());
			parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSubType.instance(SubType.ELF), HasType.instance(Type.CREATURE))));
			this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, parameters, "Search your library for any number of Elf creature cards and put them onto the battlefield. Then shuffle your library."));
		}
	}

	public NissaRevane(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(2);

		// +1: Search your library for a card named Nissa's Chosen and put it
		// onto the battlefield. Then shuffle your library.
		this.addAbility(new Search(state));

		// +1: You gain 2 life for each Elf you control.
		this.addAbility(new DoubleWellwisher(state));

		// -7: Search your library for any number of Elf creature cards and put
		// them onto the battlefield. Then shuffle your library.
		this.addAbility(new OmgElves(state));
	}
}
