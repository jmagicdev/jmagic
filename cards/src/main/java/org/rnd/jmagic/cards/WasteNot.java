package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Waste Not")
@Types({Type.ENCHANTMENT})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class WasteNot extends Card
{
	public static final class WasteNotAbility0 extends EventTriggeredAbility
	{
		public WasteNotAbility0(GameState state)
		{
			super(state, "Whenever an opponent discards a creature card, put a 2/2 black Zombie creature token onto the battlefield.");

			SetPattern opponentsStuff = new OwnedByPattern(OpponentsOf.instance(You.instance()));
			SetPattern creatureCards = new TypePattern(Type.CREATURE);
			MultipleSetPattern opponentsCreatures = new MultipleSetPattern(true);
			opponentsCreatures.addPattern(opponentsStuff);
			opponentsCreatures.addPattern(creatureCards);

			SimpleEventPattern discard = new SimpleEventPattern(EventType.DISCARD_ONE_CARD);
			discard.put(EventType.Parameter.CARD, opponentsCreatures);
			this.addPattern(discard);

			CreateTokensFactory zombie = new CreateTokensFactory(1, 2, 2, "Put a 2/2 black Zombie creature token onto the battlefield.");
			zombie.setColors(Color.BLACK);
			zombie.setSubTypes(SubType.ZOMBIE);
			this.addEffect(zombie.getEventFactory());
		}
	}

	public static final class WasteNotAbility1 extends EventTriggeredAbility
	{
		public WasteNotAbility1(GameState state)
		{
			super(state, "Whenever an opponent discards a land card, add (B)(B) to your mana pool.");

			SetPattern opponentsStuff = new OwnedByPattern(OpponentsOf.instance(You.instance()));
			SetPattern landCards = new TypePattern(Type.LAND);
			MultipleSetPattern opponentsLands = new MultipleSetPattern(true);
			opponentsLands.addPattern(opponentsStuff);
			opponentsLands.addPattern(landCards);

			SimpleEventPattern discard = new SimpleEventPattern(EventType.DISCARD_ONE_CARD);
			discard.put(EventType.Parameter.CARD, opponentsLands);
			this.addPattern(discard);

			this.addEffect(addManaToYourManaPoolFromAbility("(B)(B)"));
		}
	}

	public static final class WasteNotAbility2 extends EventTriggeredAbility
	{
		public WasteNotAbility2(GameState state)
		{
			super(state, "Whenever an opponent discards a noncreature, nonland card, draw a card.");

			SetPattern opponentsStuff = new OwnedByPattern(OpponentsOf.instance(You.instance()));
			SetPattern creatureCads = new TypePattern(Type.CREATURE);
			SetPattern landCards = new TypePattern(Type.LAND);
			MultipleSetPattern creaturesAndLands = new MultipleSetPattern(false);
			creaturesAndLands.addPattern(creatureCads);
			creaturesAndLands.addPattern(landCards);
			SetPattern nonCreatureNonLand = new RelativeComplementPattern(opponentsStuff, creaturesAndLands);

			SimpleEventPattern discard = new SimpleEventPattern(EventType.DISCARD_ONE_CARD);
			discard.put(EventType.Parameter.CARD, nonCreatureNonLand);
			this.addPattern(discard);

			this.addEffect(drawACard());
		}
	}

	public WasteNot(GameState state)
	{
		super(state);

		// Whenever an opponent discards a creature card, put a 2/2 black Zombie
		// creature token onto the battlefield.
		this.addAbility(new WasteNotAbility0(state));

		// Whenever an opponent discards a land card, add (B)(B) to your mana
		// pool.
		this.addAbility(new WasteNotAbility1(state));

		// Whenever an opponent discards a noncreature, nonland card, draw a
		// card.
		this.addAbility(new WasteNotAbility2(state));
	}
}
