package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Glint Hawk Idol")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class GlintHawkIdol extends Card
{
	public static final class GlintHawkIdolAbility0 extends EventTriggeredAbility
	{
		public GlintHawkIdolAbility0(GameState state)
		{
			super(state, "Whenever another artifact enters the battlefield under your control, you may have Glint Hawk Idol become a 2/2 Bird artifact creature with flying until end of turn.");
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), RelativeComplement.instance(HasType.instance(Type.ARTIFACT), ABILITY_SOURCE_OF_THIS), You.instance(), false));
			Animator animate = new Animator(ABILITY_SOURCE_OF_THIS, 2, 2);
			animate.addSubType(SubType.BIRD);
			animate.addType(Type.ARTIFACT);
			animate.addType(Type.CREATURE);
			animate.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(youMay(createFloatingEffect("Glint Hawk Idol becomes a 2/2 Bird artifact creature with flying until end of turn.", animate.getParts()), "You may have Glint Hawk Idol become a 2/2 Bird artifact creature with flying until end of turn."));
		}
	}

	public static final class GlintHawkIdolAbility1 extends ActivatedAbility
	{
		public GlintHawkIdolAbility1(GameState state)
		{
			super(state, "(W): Glint Hawk Idol becomes a 2/2 Bird artifact creature with flying until end of turn.");
			this.setManaCost(new ManaPool("(W)"));
			Animator animate = new Animator(ABILITY_SOURCE_OF_THIS, 2, 2);
			animate.addSubType(SubType.BIRD);
			animate.addType(Type.ARTIFACT);
			animate.addType(Type.CREATURE);
			animate.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(createFloatingEffect("Glint Hawk Idol becomes a 2/2 Bird artifact creature with flying until end of turn.", animate.getParts()));
		}
	}

	public GlintHawkIdol(GameState state)
	{
		super(state);

		// Whenever another artifact enters the battlefield under your control,
		// you may have Glint Hawk Idol become a 2/2 Bird artifact creature with
		// flying until end of turn.
		this.addAbility(new GlintHawkIdolAbility0(state));

		// (W): Glint Hawk Idol becomes a 2/2 Bird artifact creature with flying
		// until end of turn.
		this.addAbility(new GlintHawkIdolAbility1(state));
	}
}
