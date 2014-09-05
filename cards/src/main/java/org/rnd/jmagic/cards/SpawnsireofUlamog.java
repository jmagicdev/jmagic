package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Spawnsire of Ulamog")
@Types({Type.CREATURE})
@SubTypes({SubType.ELDRAZI})
@ManaCost("(10)")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class SpawnsireofUlamog extends Card
{
	public static final class SpawnEldrazi extends ActivatedAbility
	{
		public SpawnEldrazi(GameState state)
		{
			super(state, "(4): Put two 0/1 colorless Eldrazi Spawn creature tokens onto the battlefield. They have \"Sacrifice this creature: Add (1) to your mana pool.\"");
			this.setManaCost(new ManaPool("(4)"));
			this.addEffect(createEldraziSpawnTokens(numberGenerator(2), "Put two 0/1 colorless Eldrazi Spawn creature tokens onto the battlefield. They have \"Sacrifice this creature: Add (1) to your mana pool.\""));
		}
	}

	public static final class SummonEldrazi extends ActivatedAbility
	{
		public SummonEldrazi(GameState state)
		{
			super(state, "(20): Cast any number of Eldrazi cards you own from outside the game without paying their mana costs.");
			this.setManaCost(new ManaPool("(20)"));

			SetGenerator eldrazi = HasSubType.instance(SubType.ELDRAZI);
			SetGenerator eldraziCards = Intersect.instance(eldrazi, Cards.instance());
			SetGenerator castable = Intersect.instance(eldraziCards, CardsYouOwnOutsideTheGame.instance());

			EventFactory castEldrazi = new EventFactory(PLAY_WITHOUT_PAYING_MANA_COSTS, "Cast any number of Eldrazi cards you own from outside the game without paying their mana costs.");
			castEldrazi.parameters.put(EventType.Parameter.CAUSE, ABILITY_SOURCE_OF_THIS);
			castEldrazi.parameters.put(EventType.Parameter.PLAYER, You.instance());
			castEldrazi.parameters.put(EventType.Parameter.OBJECT, castable);
			this.addEffect(castEldrazi);
		}
	}

	public SpawnsireofUlamog(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(11);

		// Annihilator 1 (Whenever this creature attacks, defending player
		// sacrifices a permanent.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Annihilator.Final(state, 1));

		// (4): Put two 0/1 colorless Eldrazi Spawn creature tokens onto the
		// battlefield. They have
		// "Sacrifice this creature: Add (1) to your mana pool."
		this.addAbility(new SpawnEldrazi(state));

		// (20): Cast any number of Eldrazi cards you own from outside the game
		// without paying their mana costs.
		this.addAbility(new SummonEldrazi(state));
	}
}
