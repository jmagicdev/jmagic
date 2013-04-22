package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Trading Post")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE)})
@ColorIdentity({})
public final class TradingPost extends Card
{
	public static final class TradingPostAbility0 extends ActivatedAbility
	{
		public TradingPostAbility0(GameState state)
		{
			super(state, "(1), (T), Discard a card: You gain 4 life.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;

			// Discard a card
			EventFactory discard = discardCards(You.instance(), 1, "Discard a card");
			this.addCost(discard);

			this.addEffect(gainLife(You.instance(), 4, "You gain 4 life."));
		}
	}

	public static final class TradingPostAbility1 extends ActivatedAbility
	{
		public TradingPostAbility1(GameState state)
		{
			super(state, "(1), (T), Pay 1 life: Put a 0/1 white Goat creature token onto the battlefield.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;

			// Pay 1 life
			this.addCost(payLife(You.instance(), 1, "Pay 1 life"));

			CreateTokensFactory token = new CreateTokensFactory(1, 0, 1, "Put a 0/1 white Goat creature token onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.GOAT);
			this.addEffect(token.getEventFactory());
		}
	}

	public static final class TradingPostAbility2 extends ActivatedAbility
	{
		public TradingPostAbility2(GameState state)
		{
			super(state, "(1), (T), Sacrifice a creature: Return target artifact card from your graveyard to your hand.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;
			this.addCost(sacrificeACreature());

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.ARTIFACT), InZone.instance(GraveyardOf.instance(You.instance()))), "target artifact card in your graveyard"));

			this.addEffect(putIntoHand(target, You.instance(), "Return target artifact card from your graveyard to your hand."));
		}
	}

	public static final class TradingPostAbility3 extends ActivatedAbility
	{
		public TradingPostAbility3(GameState state)
		{
			super(state, "(1), (T), Sacrifice an artifact: Draw a card.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;

			// Sacrifice an artifact
			this.addCost(sacrifice(You.instance(), 1, HasType.instance(Type.ARTIFACT), "Sacrifice an artifact"));
			this.addEffect(drawACard());
		}
	}

	public TradingPost(GameState state)
	{
		super(state);

		// (1), (T), Discard a card: You gain 4 life.
		this.addAbility(new TradingPostAbility0(state));

		// (1), (T), Pay 1 life: Put a 0/1 white Goat creature token onto the
		// battlefield.
		this.addAbility(new TradingPostAbility1(state));

		// (1), (T), Sacrifice a creature: Return target artifact card from your
		// graveyard to your hand.
		this.addAbility(new TradingPostAbility2(state));

		// (1), (T), Sacrifice an artifact: Draw a card.
		this.addAbility(new TradingPostAbility3(state));
	}
}
