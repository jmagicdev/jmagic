package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Deathless Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("4WW")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class DeathlessAngel extends Card
{
	public static final class DeathlessAngelAbility1 extends ActivatedAbility
	{
		public DeathlessAngelAbility1(GameState state)
		{
			super(state, "(W)(W): Target creature is indestructible this turn.");

			this.setManaCost(new ManaPool("(W)(W)"));

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(createFloatingEffect("Target creature is indestructible this turn.", indestructible(targetedBy(target))));
		}
	}

	public DeathlessAngel(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(7);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (W)(W): Target creature is indestructible this turn.
		this.addAbility(new DeathlessAngelAbility1(state));
	}
}
