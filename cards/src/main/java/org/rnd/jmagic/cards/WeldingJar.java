package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Welding Jar")
@Types({Type.ARTIFACT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = Mirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class WeldingJar extends Card
{
	public static final class WeldingJarAbility0 extends ActivatedAbility
	{
		public WeldingJarAbility0(GameState state)
		{
			super(state, "Sacrifice Welding Jar: Regenerate target artifact.");
			this.addCost(sacrificeThis("Welding Jar"));

			Target target = this.addTarget(ArtifactPermanents.instance(), "target artifact");
			this.addEffect(regenerate(targetedBy(target), "Regenerate target artifact."));
		}
	}

	public WeldingJar(GameState state)
	{
		super(state);

		// Sacrifice Welding Jar: Regenerate target artifact.
		this.addAbility(new WeldingJarAbility0(state));
	}
}
