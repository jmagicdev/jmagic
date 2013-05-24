package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Voice of All")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.PLANESHIFT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class VoiceofAll extends Card
{
	public static final class ColorChoice extends org.rnd.jmagic.abilityTemplates.AsThisEntersTheBattlefieldChooseAColor
	{
		public ColorChoice(GameState state)
		{
			super(state, "Voice of All");
			this.getLinkManager().addLinkClass(ColorProtection.VoiceOfAllStatic.class);
		}
	}

	public static final class ColorProtection extends org.rnd.jmagic.abilities.keywords.Protection
	{
		public static final class VoiceOfAllStatic extends ProtectionStatic
		{
			public VoiceOfAllStatic(GameState state)
			{
				super(state, null, "the color of your choice");
				this.setQuality(new SimpleSetPattern(HasColor.instance(ChosenFor.instance(LinkedTo.instance(Identity.instance(this))))));
				this.getLinkManager().addLinkClass(ColorChoice.class);
			}
		}

		public ColorProtection(GameState state)
		{
			super(state, "the color of your choice");
		}

		@Override
		protected org.rnd.jmagic.abilities.keywords.Protection.ProtectionStatic getProtectionStatic()
		{
			return new VoiceOfAllStatic(this.state);
		}
	}

	public VoiceofAll(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new ColorChoice(state));
		this.addAbility(new ColorProtection(state));
	}
}
