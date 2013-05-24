package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Glassdust Hulk")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("3WU")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class GlassdustHulk extends Card
{
	public static final class ArtifactEqualsPump extends EventTriggeredAbility
	{
		public ArtifactEqualsPump(GameState state)
		{
			super(state, "Whenever another artifact enters the battlefield under your control, Glassdust Hulk gets +1/+1 until end of turn and is unblockable this turn.");

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), RelativeComplement.instance(HasType.instance(Type.ARTIFACT), thisCard), You.instance(), false));

			this.addEffect(createFloatingEffect("Glassdust Hulk gets +1/+1 until end of turn and is unblockable this turn.", modifyPowerAndToughness(thisCard, +1, +1), unblockable(This.instance())));
		}
	}

	public GlassdustHulk(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Whenever another artifact enters the battlefield under your control,
		// Glassdust Hulk gets +1/+1 until end of turn and is unblockable this
		// turn.
		this.addAbility(new ArtifactEqualsPump(state));

		// Cycling ((w/u))
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(WU)"));
	}
}
