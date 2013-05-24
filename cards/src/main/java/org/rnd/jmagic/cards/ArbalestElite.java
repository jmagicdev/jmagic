package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Arbalest Elite")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ARCHER})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class ArbalestElite extends Card
{
	public static final class ArbalestEliteAbility0 extends ActivatedAbility
	{
		public ArbalestEliteAbility0(GameState state)
		{
			super(state, "(2)(W), (T): Arbalest Elite deals 3 damage to target attacking or blocking creature. Arbalest Elite doesn't untap during your next untap step.");
			this.setManaCost(new ManaPool("(2)(W)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Union.instance(Attacking.instance(), Blocking.instance()), "target attacking or blocking creature"));
			this.addEffect(permanentDealDamage(3, target, "Arbalest Elite deals 3 damage to target attacking or blocking creature."));

			EventPattern untapping = new UntapDuringControllersUntapStep(ABILITY_SOURCE_OF_THIS);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(untapping));

			SetGenerator yourUntapStep = UntapStepOf.instance(You.instance());
			SetGenerator untapStepOver = Intersect.instance(PreviousStep.instance(), yourUntapStep);

			EventFactory noUntap = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Arbalest Elite doesn't untap during your next untap step.");
			noUntap.parameters.put(EventType.Parameter.CAUSE, This.instance());
			noUntap.parameters.put(EventType.Parameter.EFFECT, Identity.instance(part));
			noUntap.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(untapStepOver));
			this.addEffect(noUntap);
		}
	}

	public ArbalestElite(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// (2)(W), (T): Arbalest Elite deals 3 damage to target attacking or
		// blocking creature. Arbalest Elite doesn't untap during your next
		// untap step.
		this.addAbility(new ArbalestEliteAbility0(state));
	}
}
