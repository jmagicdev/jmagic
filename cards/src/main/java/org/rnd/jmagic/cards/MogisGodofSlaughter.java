package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mogis, God of Slaughter")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.GOD})
@ManaCost("2BR")
@ColorIdentity({Color.RED, Color.BLACK})
public final class MogisGodofSlaughter extends Card
{
	public static final class MogisGodofSlaughterAbility1 extends StaticAbility
	{
		public MogisGodofSlaughterAbility1(GameState state)
		{
			super(state, "As long as your devotion to black and red is less than seven, Mogis isn't a creature.");

			SetGenerator notEnoughDevotion = Intersect.instance(Between.instance(null, 6), DevotionTo.instance(Color.BLACK, Color.RED));
			this.canApply = Both.instance(this.canApply, notEnoughDevotion);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.CREATURE));
			this.addEffectPart(part);
		}
	}

	public static final class MogisGodofSlaughterAbility2 extends EventTriggeredAbility
	{
		public MogisGodofSlaughterAbility2(GameState state)
		{
			super(state, "At the beginning of each opponent's upkeep, Mogis deals 2 damage to that player unless he or she sacrifices a creature.");
			this.addPattern(atTheBeginningOfEachOpponentsUpkeeps());

			SetGenerator thatPlayer = OwnerOf.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.STEP));

			EventFactory damage = permanentDealDamage(2, thatPlayer, "Mogis deals 2 damage to you");
			EventFactory sacrifice = sacrifice(thatPlayer, 1, HasType.instance(Type.CREATURE), "Sacrifice a creature");
			this.addEffect(unless(thatPlayer, damage, sacrifice, "Mogis deals 2 damage to that player unless he or she sacrifices a creature."));
		}
	}

	public MogisGodofSlaughter(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(5);

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// As long as your devotion to black and red is less than seven, Mogis
		// isn't a creature.
		this.addAbility(new MogisGodofSlaughterAbility1(state));

		// At the beginning of each opponent's upkeep, Mogis deals 2 damage to
		// that player unless he or she sacrifices a creature.
		this.addAbility(new MogisGodofSlaughterAbility2(state));
	}
}
