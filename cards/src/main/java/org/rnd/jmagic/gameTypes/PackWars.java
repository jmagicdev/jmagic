package org.rnd.jmagic.gameTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.gameTypes.packWars.*;

public class PackWars extends GameType
{
	public PackWars()
	{
		super("Pack Wars (M13)");

		Expansion expansion = new Magic2013();
		this.addRule(new GameRules(new ExpansionBoosterFactory(expansion), new ExpansionBoosterFactory(expansion), new LandBoosterFactory(3)));
	}

	@Name("Pack wars")
	@Description("Initial decks are replaced with the shuffled contents of boosters created by booster factories")
	public static class GameRules extends GameType.SimpleGameTypeRule
	{
		private java.util.List<BoosterFactory> boosterFactories;

		public GameRules()
		{
			this.boosterFactories = new java.util.LinkedList<BoosterFactory>();
		}

		public GameRules(BoosterFactory... boosterFactories)
		{
			this();
			this.setBoosterFactories(boosterFactories);
		}

		public BoosterFactory[] getBoosterFactories()
		{
			return this.boosterFactories.toArray(new BoosterFactory[this.boosterFactories.size()]);
		}

		public BoosterFactory getBoosterFactories(int index)
		{
			return this.boosterFactories.get(index);
		}

		@Override
		public void modifyGameState(GameState physicalState)
		{
			try
			{
				for(Player player: physicalState.players)
				{
					Zone library = player.getLibrary(physicalState);
					for(GameObject o: library)
						physicalState.game.removeObject(o.getActual());
					library.objects.clear();

					Zone sideboard = player.getSideboard(physicalState);
					for(GameObject o: sideboard)
						physicalState.game.removeObject(o.getActual());
					sideboard.objects.clear();

					java.util.List<Card> deck = new java.util.LinkedList<Card>();

					for(BoosterFactory factory: this.boosterFactories)
						deck.addAll(factory.createBooster(physicalState));

					java.util.Collections.shuffle(deck);

					for(Card card: deck)
					{
						card.setOwner(player);
						library.addToTop(card);
					}
				}
			}
			catch(org.rnd.jmagic.CardLoader.CardLoaderException ex)
			{
				throw new RuntimeException(ex);
			}
		}

		public void setBoosterFactories(BoosterFactory[] factories)
		{
			this.boosterFactories = java.util.Arrays.asList(factories);
		}

		public void setBoosterFactories(int index, BoosterFactory factory)
		{
			while(index >= this.boosterFactories.size())
				this.boosterFactories.add(null);
			this.boosterFactories.set(index, factory);
		}
	}
}
