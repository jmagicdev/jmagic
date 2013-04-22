package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Springjack Pasture")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.EVENTIDE, r = Rarity.RARE)})
@ColorIdentity({})
public final class SpringjackPasture extends Card
{
	public static final class MakeGoat extends ActivatedAbility
	{
		public MakeGoat(GameState state)
		{
			super(state, "(4), (T): Put a 0/1 white Goat creature token onto the battlefield");

			this.costsTap = true;
			this.setManaCost(new ManaPool("4"));

			CreateTokensFactory token = new CreateTokensFactory(1, 0, 1, "Put a 0/1 white Goat creature token onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.GOAT);
			this.addEffect(token.getEventFactory());
		}
	}

	public static final class SacGoats extends ActivatedAbility
	{
		public SacGoats(GameState state)
		{
			super(state, "(T), Sacrifice X Goats: Add X mana of any one color to your mana pool. You gain X life.");

			this.costsTap = true;

			SetGenerator X = ValueOfX.instance(This.instance());
			SetGenerator goats = HasSubType.instance(SubType.GOAT);
			SetGenerator controlled = ControlledBy.instance(You.instance());
			SetGenerator goatPermanents = Intersect.instance(goats, Permanents.instance());
			SetGenerator controllersGoats = Intersect.instance(controlled, goatPermanents);

			EventFactory factory = new EventFactory(EventType.SACRIFICE_CHOICE, "Sacrifice X Goats.");
			factory.parameters.put(EventType.Parameter.CHOICE, controllersGoats);
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, X);
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.usesX();
			this.addCost(factory);

			EventType.ParameterMap manaParameters = new EventType.ParameterMap();
			manaParameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			manaParameters.put(EventType.Parameter.MANA, Identity.instance(new ManaPool("(WUBRG)")));
			manaParameters.put(EventType.Parameter.NUMBER, X);
			manaParameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(new EventFactory(EventType.ADD_MANA, manaParameters, "Add X mana of any one color to your mana pool."));

			this.addEffect(gainLife(You.instance(), X, "Gain X life."));
		}
	}

	public SpringjackPasture(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));
		this.addAbility(new MakeGoat(state));
		this.addAbility(new SacGoats(state));
	}
}
