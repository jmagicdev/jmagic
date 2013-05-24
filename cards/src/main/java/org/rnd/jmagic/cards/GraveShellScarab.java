package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Grave-Shell Scarab")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("2BGG")
@Printings({@Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class GraveShellScarab extends Card
{
	public static final class SacDraw extends ActivatedAbility
	{
		public SacDraw(GameState state)
		{
			super(state, "(1), Sacrifice Grave-Shell Scarab: Draw a card.");

			this.setManaCost(new ManaPool("1"));

			this.addCost(sacrificeThis("Grave-Shell Scarab"));

			this.addEffect(drawACard());
		}
	}

	public GraveShellScarab(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new SacDraw(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Dredge(state, 1));
	}
}
