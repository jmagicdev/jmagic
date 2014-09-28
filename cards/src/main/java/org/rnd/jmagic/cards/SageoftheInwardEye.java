package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Sage of the Inward Eye")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.DJINN})
@ManaCost("2URW")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLUE})
public final class SageoftheInwardEye extends Card
{
	public static final class SageoftheInwardEyeAbility1 extends EventTriggeredAbility
	{
		public SageoftheInwardEyeAbility1(GameState state)
		{
			super(state, "Whenever you cast a noncreature spell, creatures you control gain lifelink until end of turn.");
			this.addPattern(whenYouCastANoncreatureSpell());
			this.addEffect(addAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Lifelink.class, "Whenever you cast a noncreature spell, creatures you control gain lifelink until end of turn."));
		}
	}

	public SageoftheInwardEye(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever you cast a noncreature spell, creatures you control gain
		// lifelink until end of turn.
		this.addAbility(new SageoftheInwardEyeAbility1(state));
	}
}
