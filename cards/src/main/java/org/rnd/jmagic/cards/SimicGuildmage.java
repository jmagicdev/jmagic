package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Simic Guildmage")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.ELF})
@ManaCost("(GU)(GU)")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class SimicGuildmage extends Card
{
	public static final class MoveCounter extends ActivatedAbility
	{
		public MoveCounter(GameState state)
		{
			super(state, "(1)(G): Move a +1/+1 counter from target creature onto another target creature with the same controller.");

			this.setManaCost(new ManaPool("1G"));

			Target source = this.addTarget(CreaturePermanents.instance(), "target creature to move a +1/+1 counter off of");
			source.restrictFromLaterTargets = true;
			SetGenerator firstTarget = targetedBy(source);

			Target destination = this.addTarget(Intersect.instance(ControlledBy.instance(ControllerOf.instance(firstTarget)), CreaturePermanents.instance()), "target creature to move the +1/+1 counter onto");
			SetGenerator secondTarget = targetedBy(destination);

			EventType.ParameterMap counterParams = new EventType.ParameterMap();
			counterParams.put(EventType.Parameter.CAUSE, This.instance());
			counterParams.put(EventType.Parameter.FROM, firstTarget);
			counterParams.put(EventType.Parameter.TO, secondTarget);
			counterParams.put(EventType.Parameter.NUMBER, numberGenerator(1));
			counterParams.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			this.addEffect(new EventFactory(EventType.MOVE_COUNTERS, counterParams, "Move a +1/+1 counter from target creature onto another target creature with the same controller"));
		}
	}

	public static final class MoveAura extends ActivatedAbility
	{

		public MoveAura(GameState state)
		{
			super(state, "(1)(U): Attach target Aura enchanting a permanent to another permanent with the same controller.");

			this.setManaCost(new ManaPool("1U"));

			Target target = this.addTarget(AurasEnchantingPermanents.instance(), "target Aura enchanting a permanent");

			SetGenerator originalBearer = EnchantedBy.instance(targetedBy(target));
			SetGenerator choices = RelativeComplement.instance(Intersect.instance(Permanents.instance(), ControlledBy.instance(ControllerOf.instance(originalBearer))), originalBearer);

			EventType.ParameterMap chooseParameters = new EventType.ParameterMap();
			chooseParameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			chooseParameters.put(EventType.Parameter.PLAYER, You.instance());
			chooseParameters.put(EventType.Parameter.CHOICE, choices);
			chooseParameters.put(EventType.Parameter.TYPE, Identity.instance(PlayerInterface.ChoiceType.OBJECTS));
			this.addEffect(new EventFactory(EventType.ATTACH_TO_CHOICE, chooseParameters, "Attach target Aura enchanting a permanent to another permanent with the same controller"));
		}
	}

	public SimicGuildmage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new MoveCounter(state));
		this.addAbility(new MoveAura(state));
	}
}
