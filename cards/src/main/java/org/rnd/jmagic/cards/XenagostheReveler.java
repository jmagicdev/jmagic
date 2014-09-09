package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Xenagos, the Reveler")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.XENAGOS})
@ManaCost("2RG")
@ColorIdentity({Color.RED, Color.GREEN})
public final class XenagostheReveler extends Card
{
	public static final class XenagostheRevelerAbility0 extends LoyaltyAbility
	{
		public XenagostheRevelerAbility0(GameState state)
		{
			super(state, +1, "Add X mana in any combination of (R) and/or (G) to your mana pool, where X is the number of creatures you control.");

			EventFactory mana = new EventFactory(EventType.ADD_MANA, "Add two mana in any combination of colors to your mana pool.");
			mana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			mana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			mana.parameters.put(EventType.Parameter.MULTIPLY, Count.instance(CREATURES_YOU_CONTROL));
			mana.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("(RG)")));
			this.addEffect(mana);
		}
	}

	public static final class XenagostheRevelerAbility1 extends LoyaltyAbility
	{
		public XenagostheRevelerAbility1(GameState state)
		{
			super(state, 0, "Put a 2/2 red and green Satyr creature token with haste onto the battlefield.");

			CreateTokensFactory satyr = new CreateTokensFactory(1, 2, 2, "Put a 2/2 red and green Satyr creature token with haste onto the battlefield.");
			satyr.setColors(Color.RED, Color.GREEN);
			satyr.setSubTypes(SubType.SATYR);
			satyr.addAbility(org.rnd.jmagic.abilities.keywords.Haste.class);
			this.addEffect(satyr.getEventFactory());
		}
	}

	public static final class XenagostheRevelerAbility2 extends LoyaltyAbility
	{
		public XenagostheRevelerAbility2(GameState state)
		{
			super(state, -6, "Exile the top seven cards of your library. You may put any number of creature and/or land cards from among them onto the battlefield.");

			SetGenerator topSeven = TopCards.instance(7, LibraryOf.instance(You.instance()));
			EventFactory exile = exile(topSeven, "Exile the top seven cards of your library.");
			this.addEffect(exile);

			SetGenerator exiled = NewObjectOf.instance(EffectResult.instance(exile));
			SetGenerator thingsToDrop = Intersect.instance(HasType.instance(Type.CREATURE, Type.LAND), exiled);

			EventFactory drop = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "You may put any number of creature and/or land cards from among them onto the battlefield.");
			drop.parameters.put(EventType.Parameter.CAUSE, This.instance());
			drop.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			drop.parameters.put(EventType.Parameter.OBJECT, thingsToDrop);
			drop.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, null));
			this.addEffect(drop);
		}
	}

	public XenagostheReveler(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(3);

		// +1: Add X mana in any combination of (R) and/or (G) to your mana
		// pool, where X is the number of creatures you control.
		this.addAbility(new XenagostheRevelerAbility0(state));

		// 0: Put a 2/2 red and green Satyr creature token with haste onto the
		// battlefield.
		this.addAbility(new XenagostheRevelerAbility1(state));

		// -6: Exile the top seven cards of your library. You may put any number
		// of creature and/or land cards from among them onto the battlefield.
		this.addAbility(new XenagostheRevelerAbility2(state));
	}
}
