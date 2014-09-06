package org.rnd.jmagic.engine;

public class SplitCard extends Card
{
	public SplitCard(GameState state, Class<? extends Card> left, Class<? extends Card> right)
	{
		super(state);

		this.characteristics = new Characteristics[] {//
		Characteristics.createFromClass(state.game, left, this, false), // left
		Characteristics.createFromClass(state.game, right, this, false) // right;
		};

		this.setName(java.util.Arrays.stream(this.characteristics).map(t -> t.name).reduce((l, r) -> l + " // " + r).orElse(""));
	}
}
