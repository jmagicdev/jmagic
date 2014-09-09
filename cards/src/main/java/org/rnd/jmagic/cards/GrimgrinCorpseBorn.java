package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Grimgrin, Corpse-Born")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.WARRIOR})
@ManaCost("3UB")
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class GrimgrinCorpseBorn extends Card
{
	public static final class GrimgrinCorpseBornAbility0 extends StaticAbility
	{
		public GrimgrinCorpseBornAbility0(GameState state)
		{
			super(state, "Grimgrin, Corpse-Born enters the battlefield tapped and doesn't untap during your untap step.");

			ZoneChangeReplacementEffect tapped = new ZoneChangeReplacementEffect(this.game, "Grimgrin, Corpse-Born enters the battlefield tapped");
			tapped.addPattern(asThisEntersTheBattlefield());
			tapped.addEffect(tap(NewObjectOf.instance(tapped.replacedByThis()), "Grimgrin, Corpse-Born enters the battlefield tapped"));
			this.addEffectPart(replacementEffectPart(tapped));

			EventPattern prohibitPattern = new UntapDuringControllersUntapStep(This.instance());
			ContinuousEffect.Part doesntUntap = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			doesntUntap.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(doesntUntap);

			this.canApply = NonEmpty.instance();
		}
	}

	public static final class GrimgrinCorpseBornAbility1 extends ActivatedAbility
	{
		public GrimgrinCorpseBornAbility1(GameState state)
		{
			super(state, "Sacrifice another creature: Untap Grimgrin and put a +1/+1 counter on it.");
			// Sacrifice another creature
			this.addCost(sacrifice(You.instance(), 1, RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS), "Sacrifice another creature"));
			this.addEffect(untap(ABILITY_SOURCE_OF_THIS, "Untap Grimgrin"));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "and put a +1/+1 counter on Grimgrin."));
		}
	}

	public static final class GrimgrinCorpseBornAbility2 extends EventTriggeredAbility
	{
		public GrimgrinCorpseBornAbility2(GameState state)
		{
			super(state, "Whenever Grimgrin attacks, destroy target creature defending player controls, then put a +1/+1 counter on Grimgrin.");
			this.addPattern(whenThisAttacks());

			SetGenerator controlledByDefendingPlayer = ControlledBy.instance(DefendingPlayer.instance(ABILITY_SOURCE_OF_THIS));
			SetGenerator defendingCreatures = Intersect.instance(controlledByDefendingPlayer, CreaturePermanents.instance());
			SetGenerator target = targetedBy(this.addTarget(defendingCreatures, "target creature defending player controls"));
			this.addEffect(destroy(target, "Destroy target creature defending player controls,"));

			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "then put a +1/+1 counter on Grimgrin."));
		}
	}

	public GrimgrinCorpseBorn(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Grimgrin, Corpse-Born enters the battlefield tapped and doesn't untap
		// during your untap step.
		this.addAbility(new GrimgrinCorpseBornAbility0(state));

		// Sacrifice another creature: Untap Grimgrin and put a +1/+1 counter on
		// it.
		this.addAbility(new GrimgrinCorpseBornAbility1(state));

		// Whenever Grimgrin attacks, destroy target creature defending player
		// controls, then put a +1/+1 counter on Grimgrin.
		this.addAbility(new GrimgrinCorpseBornAbility2(state));
	}
}
