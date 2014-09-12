package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Aeronaut Tinkerer")
@Types({Type.CREATURE})
@SubTypes({SubType.ARTIFICER, SubType.HUMAN})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class AeronautTinkerer extends Card
{
	public static final class AeronautTinkererAbility0 extends StaticAbility
	{
		public AeronautTinkererAbility0(GameState state)
		{
			super(state, "Aeronaut Tinkerer has flying as long as you control an artifact.");

			SetGenerator youControlAnArtifact = Intersect.instance(HasType.instance(Type.ARTIFACT), ControlledBy.instance(You.instance()));
			this.canApply = Both.instance(this.canApply, youControlAnArtifact);

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public AeronautTinkerer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Aeronaut Tinkerer has flying as long as you control an artifact. (It
		// can't be blocked except by creatures with flying or reach.)
		this.addAbility(new AeronautTinkererAbility0(state));
	}
}
