package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shapesharer")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Shapesharer extends Card
{
	public static final class ResidualSelfImage extends ActivatedAbility
	{
		public static final class DelayedEnd extends UntilNextTurn.EventAndBeginTurnTracker
		{
			public DelayedEnd()
			{
				super(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT);
			}
		}

		public ResidualSelfImage(GameState state)
		{
			super(state, "(2)(U): Target Shapeshifter becomes a copy of target creature until your next turn.");

			this.setManaCost(new ManaPool("2U"));

			Target shapeshifterTarget = this.addTarget(Intersect.instance(InZone.instance(Battlefield.instance()), HasSubType.instance(SubType.SHAPESHIFTER)), "target Shapeshifter");
			Target creatureTarget = this.addTarget(CreaturePermanents.instance(), "target creature");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COPY_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.ORIGINAL, targetedBy(creatureTarget));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(shapeshifterTarget));

			SetGenerator expires = Not.instance(Intersect.instance(This.instance(), UntilNextTurn.instance(DelayedEnd.class)));
			state.ensureTracker(new DelayedEnd());

			this.addEffect(createFloatingEffect(expires, "Target Shapeshifter becomes a copy of target creature until your next turn", part));
		}
	}

	public Shapesharer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Changeling(state));
		this.addAbility(new ResidualSelfImage(state));
	}
}
