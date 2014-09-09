package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Archivist")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("2UU")
@ColorIdentity({Color.BLUE})
public final class Archivist extends Card
{
	public static final class ArchivistAbility0 extends ActivatedAbility
	{
		public ArchivistAbility0(GameState state)
		{
			super(state, "(T): Draw a card.");
			this.costsTap = true;
			this.addEffect(drawACard());
		}
	}

	public Archivist(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Draw a card.
		this.addAbility(new ArchivistAbility0(state));
	}
}
