package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("New Prahv Guildmage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("WU")
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class NewPrahvGuildmage extends Card
{
	public static final class NewPrahvGuildmageAbility0 extends ActivatedAbility
	{
		public NewPrahvGuildmageAbility0(GameState state)
		{
			super(state, "(W)(U): Target creature gains flying until end of turn.");
			this.setManaCost(new ManaPool("(W)(U)"));

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Flying.class, "Target creature gains flying until end of turn."));
		}
	}

	public static final class NewPrahvGuildmageAbility1 extends ActivatedAbility
	{
		public NewPrahvGuildmageAbility1(GameState state)
		{
			super(state, "(3)(W)(U): Detain target nonland permanent an opponent controls.");
			this.setManaCost(new ManaPool("(3)(W)(U)"));

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND)), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "target nonland permanent an opponent controls"));

			this.addEffect(detain(target, "Detain target nonland permanent an opponent controls."));

			state.ensureTracker(new DetainGenerator.Tracker());
		}
	}

	public NewPrahvGuildmage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (W)(U): Target creature gains flying until end of turn.
		this.addAbility(new NewPrahvGuildmageAbility0(state));

		// (3)(W)(U): Detain target nonland permanent an opponent controls.
		// (Until your next turn, that permanent can't attack or block and its
		// activated abilities can't be activated.)
		this.addAbility(new NewPrahvGuildmageAbility1(state));
	}
}
