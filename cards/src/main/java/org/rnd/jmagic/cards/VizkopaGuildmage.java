package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Vizkopa Guildmage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("WB")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class VizkopaGuildmage extends Card
{
	public static final class VizkopaGuildmageAbility0 extends ActivatedAbility
	{
		public VizkopaGuildmageAbility0(GameState state)
		{
			super(state, "(1)(W)(B): Target creature gains lifelink until end of turn.");
			this.setManaCost(new ManaPool("(1)(W)(B)"));

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Lifelink.class, "Target creature gains lifelink until end of turn."));
		}
	}

	public static final class VizkopaGuildmageAbility1 extends ActivatedAbility
	{
		public VizkopaGuildmageAbility1(GameState state)
		{
			super(state, "(1)(W)(B): Whenever you gain life this turn, each opponent loses that much life.");
			this.setManaCost(new ManaPool("(1)(W)(B)"));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.GAIN_LIFE);
			pattern.put(EventType.Parameter.PLAYER, You.instance());

			SetGenerator delayedTrigger = This.instance();
			SetGenerator lifeGained = EventResult.instance(TriggerEvent.instance(delayedTrigger));
			EventFactory loseLife = loseLife(OpponentsOf.instance(You.instance()), lifeGained, "Each opponent loses that much life.");

			EventFactory createDelayedTrigger = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Whenever you gain life this turn, each opponent loses that much life.");
			createDelayedTrigger.parameters.put(EventType.Parameter.CAUSE, This.instance());
			createDelayedTrigger.parameters.put(EventType.Parameter.EVENT, Identity.instance(pattern));
			createDelayedTrigger.parameters.put(EventType.Parameter.EFFECT, Identity.instance(loseLife));
			createDelayedTrigger.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(EndMostFloatingEffects.instance()));
			this.addEffect(createDelayedTrigger);
		}
	}

	public VizkopaGuildmage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (1)(W)(B): Target creature gains lifelink until end of turn.
		this.addAbility(new VizkopaGuildmageAbility0(state));

		// (1)(W)(B): Whenever you gain life this turn, each opponent loses that
		// much life.
		this.addAbility(new VizkopaGuildmageAbility1(state));
	}
}
