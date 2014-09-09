package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Death-Hood Cobra")
@Types({Type.CREATURE})
@SubTypes({SubType.SNAKE})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class DeathHoodCobra extends Card
{
	public static final class DeathHoodCobraAbility0 extends ActivatedAbility
	{
		public DeathHoodCobraAbility0(GameState state)
		{
			super(state, "(1)(G): Death-Hood Cobra gains reach until end of turn.");
			this.setManaCost(new ManaPool("(1)(G)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Reach.class, "Death-Hood Cobra gains reach until end of turn."));
		}
	}

	public static final class DeathHoodCobraAbility1 extends ActivatedAbility
	{
		public DeathHoodCobraAbility1(GameState state)
		{
			super(state, "(1)(G): Death-Hood Cobra gains deathtouch until end of turn.");
			this.setManaCost(new ManaPool("(1)(G)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Deathtouch.class, "Death-Hood Cobra gains deathtouch until end of turn."));
		}
	}

	public DeathHoodCobra(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (1)(G): Death-Hood Cobra gains reach until end of turn. (It can block
		// creatures with flying.)
		this.addAbility(new DeathHoodCobraAbility0(state));

		// (1)(G): Death-Hood Cobra gains deathtouch until end of turn. (Any
		// amount of damage it deals to a creature is enough to destroy it.)
		this.addAbility(new DeathHoodCobraAbility1(state));
	}
}
