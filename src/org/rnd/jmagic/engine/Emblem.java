package org.rnd.jmagic.engine;

public final class Emblem extends GameObject
{
	/**
	 * Create an emblem.
	 * 
	 * @param state The gamestate in which the emblem exists.
	 */
	public Emblem(GameState state)
	{
		super(state);
		this.setName("");

		// No rule actually says this, but the rules and cards seem to heavily
		// imply it.
		for(Player p: state.players)
		{
			this.setActualVisibility(p, true);
			this.setPhysicalVisibility(p, true);
		}
	}

	/**
	 * 113.3. An emblem has no characteristics other than the abilities defined
	 * by the effect that created it. In particular, an emblem has ... no mana
	 * cost ... .
	 * 
	 * @return 0.
	 */
	@Override
	public int getConvertedManaCost()
	{
		return 0;
	}
}
