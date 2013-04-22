package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Back from the Brink")
@Types({Type.ENCHANTMENT})
@ManaCost("4UU")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class BackfromtheBrink extends Card
{
	public static final class BackfromtheBrinkAbility0 extends ActivatedAbility
	{
		public BackfromtheBrinkAbility0(GameState state)
		{
			super(state, "Exile a creature card from your graveyard and pay its mana cost: Put a token onto the battlefield that's a copy of that card. Activate this ability only any time you could cast a sorcery.");
			// Exile a creature card from your graveyard and pay its mana cost

			SetGenerator creatures = HasType.instance(Type.CREATURE);
			SetGenerator inYourGraveyard = InZone.instance(GraveyardOf.instance(You.instance()));
			SetGenerator creatureCardsInYourGraveyard = Intersect.instance(creatures, Cards.instance(), inYourGraveyard);
			EventFactory exile = exile(You.instance(), creatureCardsInYourGraveyard, 1, "Exile a creature card from your graveyard");

			SetGenerator exiledCard = OldObjectOf.instance(EffectResult.instance(exile));
			EventFactory payMana = new EventFactory(EventType.PAY_MANA_COST, "and pay its mana cost");
			payMana.parameters.put(EventType.Parameter.CAUSE, This.instance());
			payMana.parameters.put(EventType.Parameter.OBJECT, exiledCard);
			payMana.parameters.put(EventType.Parameter.PLAYER, You.instance());

			// normally, players get to choose the order in which they pay the
			// costs for a spell or abiity. this is problematic here as the
			// "its" in "its mana cost" is determined by checking what was
			// exiled. so we're enforcing an order for these costs by using a
			// sequence.
			this.addCost(sequence(exile, payMana));

			EventFactory token = new EventFactory(EventType.CREATE_TOKEN_COPY, "Put a token onto the battlefield that's a copy of that card.");
			token.parameters.put(EventType.Parameter.CAUSE, This.instance());
			token.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			token.parameters.put(EventType.Parameter.OBJECT, exiledCard);
			this.addEffect(token);

			this.activateOnlyAtSorcerySpeed();
		}
	}

	public BackfromtheBrink(GameState state)
	{
		super(state);

		// Exile a creature card from your graveyard and pay its mana cost: Put
		// a token onto the battlefield that's a copy of that card. Activate
		// this ability only any time you could cast a sorcery.
		this.addAbility(new BackfromtheBrinkAbility0(state));
	}
}
