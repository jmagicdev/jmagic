package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Venser, the Sojourner")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.VENSER})
@ManaCost("3WU")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class VensertheSojourner extends Card
{
	public static final class VensertheSojournerAbility0 extends LoyaltyAbility
	{
		public VensertheSojournerAbility0(GameState state)
		{
			super(state, +2, "Exile target permanent you own. Return it to the battlefield under your control at the beginning of the next end step.");

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(Permanents.instance(), OwnedBy.instance(You.instance())), "target permanent you own"));

			EventFactory factory = new EventFactory(SLIDE, "Exile target permanent you own. Return it to the battlefield under your control at the beginning of the next end step.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.TARGET, target);
			this.addEffect(factory);
		}
	}

	public static final class VensertheSojournerAbility1 extends LoyaltyAbility
	{
		public VensertheSojournerAbility1(GameState state)
		{
			super(state, -1, "Creatures can't be blocked this turn.");

			this.addEffect(createFloatingEffect("Creatures can't be blocked this turn.", unblockable(CreaturePermanents.instance())));
		}
	}

	public static final class VensertheSojournerAbility2 extends LoyaltyAbility
	{
		public static final class Slide extends EventTriggeredAbility
		{
			public Slide(GameState state)
			{
				super(state, "Whenever you cast a spell, exile target permanent.");

				this.addPattern(whenYouCastASpell());

				SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));

				this.addEffect(exile(target, "Exile target permanent."));
			}
		}

		public VensertheSojournerAbility2(GameState state)
		{
			super(state, -8, "You get an emblem with \"Whenever you cast a spell, exile target permanent.\"");

			EventFactory makeEmblem = new EventFactory(EventType.CREATE_EMBLEM, "You get an emblem with \"Whenever you cast a spell, exile target permanent.\"");
			makeEmblem.parameters.put(EventType.Parameter.CAUSE, This.instance());
			makeEmblem.parameters.put(EventType.Parameter.ABILITY, Identity.instance(Slide.class));
			makeEmblem.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			this.addEffect(makeEmblem);
		}
	}

	public VensertheSojourner(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(3);

		// +2: Exile target permanent you own. Return it to the battlefield
		// under your control at the beginning of the next end step.
		this.addAbility(new VensertheSojournerAbility0(state));

		// -1: Creatures are unblockable this turn.
		this.addAbility(new VensertheSojournerAbility1(state));

		// -8: You get an emblem with "Whenever you cast a spell, exile target
		// permanent."
		this.addAbility(new VensertheSojournerAbility2(state));
	}
}
