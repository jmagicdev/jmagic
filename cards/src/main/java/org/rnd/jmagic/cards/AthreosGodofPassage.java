package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Athreos, God of Passage")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE, Type.ENCHANTMENT})
@SubTypes({SubType.GOD})
@ManaCost("1WB")
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class AthreosGodofPassage extends Card
{
	public static final class AthreosGodofPassageAbility1 extends StaticAbility
	{
		public AthreosGodofPassageAbility1(GameState state)
		{
			super(state, "As long as your devotion to white and black is less than seven, Athreos isn't a creature.");

			SetGenerator notEnoughDevotion = Intersect.instance(Between.instance(null, 6), DevotionTo.instance(Color.WHITE, Color.BLACK));
			this.canApply = Both.instance(this.canApply, notEnoughDevotion);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.CREATURE));
			this.addEffectPart(part);
		}
	}

	public static final class AthreosGodofPassageAbility2 extends EventTriggeredAbility
	{
		public AthreosGodofPassageAbility2(GameState state)
		{
			super(state, "Whenever another creature you own dies, return it to your hand unless target opponent pays 3 life.");

			SetGenerator yourCreatures = Intersect.instance(OwnedBy.instance(You.instance()), HasType.instance(Type.CREATURE));
			this.addPattern(whenXDies(yourCreatures));

			SetGenerator died = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			EventFactory toHand = putIntoHand(died, You.instance(), "Return that creature to its owner's hand.");

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			EventFactory payLife = payLife(target, 3, "Pay 3 life");

			this.addEffect(unless(target, toHand, payLife, "Return that creature to your hand unless target opponent pays 3 life."));
		}
	}

	public AthreosGodofPassage(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// As long as your devotion to white and black is less than seven,
		// Athreos isn't a creature.
		this.addAbility(new AthreosGodofPassageAbility1(state));

		// Whenever another creature you own dies, return it to your hand unless
		// target opponent pays 3 life.
		this.addAbility(new AthreosGodofPassageAbility2(state));
	}
}
