package org.rnd.jmagic.engine;

import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.gameTypes.packWars.*;

public class GameTypes
{
	public static final GameType BLOCK = new GameType("ISD Block Constructed");
	public static final GameType STANDARD = new GameType("Standard");
	public static final GameType MODERN = new GameType("Modern");
	public static final GameType LEGACY = new GameType("Legacy");
	public static final GameType VINTAGE = new GameType("Vintage");
	public static final GameType OPEN = new GameType("Open");
	public static final GameType PACK_WARS = new GameType("Pack Wars (M13)");
	public static final GameType STACKED = new GameType("Stacked (cheater!)");
	public static final GameType VINTAGE_PACK_WARS = new GameType("Vintage Pack Wars");
	private static final GameType[] oldValues = {BLOCK, STANDARD, MODERN, LEGACY, VINTAGE, OPEN, STACKED, PACK_WARS, VINTAGE_PACK_WARS};

	static
	{
		BLOCK.addRule(new DeckSizeMinimum(60));
		BLOCK.addRule(new MaximumCardCount(4));
		BLOCK.addRule(new SideboardSize(15));
		BLOCK.addRule(new SideboardAsWishboard());
		BLOCK.addRule(new CardPool.Block());

		STANDARD.addRule(new DeckSizeMinimum(60));
		STANDARD.addRule(new MaximumCardCount(4));
		STANDARD.addRule(new SideboardSize(15));
		STANDARD.addRule(new SideboardAsWishboard());
		STANDARD.addRule(new CardPool.Standard());

		MODERN.addRule(new DeckSizeMinimum(60));
		MODERN.addRule(new MaximumCardCount(4));
		MODERN.addRule(new SideboardSize(15));
		MODERN.addRule(new SideboardAsWishboard());
		MODERN.addRule(new CardPool.Modern());

		LEGACY.addRule(new DeckSizeMinimum(60));
		LEGACY.addRule(new MaximumCardCount(4));
		LEGACY.addRule(new SideboardSize(15));
		LEGACY.addRule(new SideboardAsWishboard());
		LEGACY.addRule(new CardPool.Legacy());

		VINTAGE.addRule(new DeckSizeMinimum(60));
		VINTAGE.addRule(new MaximumCardCount(4));
		VINTAGE.addRule(new SideboardSize(15));
		VINTAGE.addRule(new SideboardAsWishboard());
		VINTAGE.addRule(new CardPool.Vintage());

		OPEN.addRule(new SideboardAsWishboard());

		STACKED.addRule(new Stacked());
		STACKED.addRule(new SideboardAsWishboard());

		PACK_WARS.addRule(new PackWars(new ExpansionBoosterFactory(Expansion.MAGIC_2013), new ExpansionBoosterFactory(Expansion.MAGIC_2013), new LandBoosterFactory(3)));

		VINTAGE_PACK_WARS.addRule(new PackWars(new VintageBoosterFactory(), new VintageBoosterFactory(), new LandBoosterFactory(3)));
		VINTAGE_PACK_WARS.addRule(new UtopiaLands());
	}

	// TODO : This is not the right way to figure out what game types are
	// available. Perhaps we make a GameFactory (*ducks tomato*) to register
	// game types with, and add a GameFactory parameter to the GameType
	// constructor?
	public static GameType[] values()
	{
		return oldValues;
	}
}
