package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Colossus of Sardia")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.GOLEM})
@ManaCost("9")
@ColorIdentity({})
public final class ColossusofSardia extends Card
{
	public static final class Waken extends ActivatedAbility
	{
		public Waken(GameState state)
		{
			super(state, "(9): Untap Colossus of Sardia. Activate this ability only during your upkeep.");
			this.setManaCost(new ManaPool("9"));
			this.addEffect(untap(ABILITY_SOURCE_OF_THIS, "Untap Colossus of Sardia."));
			this.activateOnlyDuringYourUpkeep();
		}
	}

	public ColossusofSardia(GameState state)
	{
		super(state);

		this.setPower(9);
		this.setToughness(9);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
		this.addAbility(new org.rnd.jmagic.abilities.DoesntUntapDuringYourUntapStep(state, "Colossus of Sardia"));
		this.addAbility(new Waken(state));
	}
}
