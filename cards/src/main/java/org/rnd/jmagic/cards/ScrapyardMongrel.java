package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scrapyard Mongrel")
@Types({Type.CREATURE})
@SubTypes({SubType.HOUND})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class ScrapyardMongrel extends Card
{
	public static final class ScrapyardMongrelAbility0 extends StaticAbility
	{
		public ScrapyardMongrelAbility0(GameState state)
		{
			super(state, "As long as you control an artifact, Scrapyard Mongrel gets +2/+0 and has trample.");

			SetGenerator artifact = HasType.instance(Type.ARTIFACT);
			SetGenerator youControlArtifact = Intersect.instance(ControlledBy.instance(You.instance()), artifact);
			this.canApply = Both.instance(this.canApply, youControlArtifact);

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +2, +0));
			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public ScrapyardMongrel(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// As long as you control an artifact, Scrapyard Mongrel gets +2/+0 and
		// has trample. (If it would assign enough damage to its blockers to
		// destroy them, you may have it assign the rest of its damage to
		// defending player or planeswalker.)
		this.addAbility(new ScrapyardMongrelAbility0(state));
	}
}
