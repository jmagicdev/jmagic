package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.SimpleEventPattern;

@Name("Godsend")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1WW")
@ColorIdentity({Color.WHITE})
public final class Godsend extends Card
{
	public static final class GodsendAbility0 extends StaticAbility
	{
		public GodsendAbility0(GameState state)
		{
			super(state, "Equipped creature gets +3/+3.");
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +3, +3));
		}
	}

	public static final class GodsendAbility1 extends EventTriggeredAbility
	{
		public GodsendAbility1(GameState state)
		{
			super(state, "Whenever equipped creature blocks or becomes blocked by one or more creatures, you may exile one of those creatures.");

			this.addPattern(whenThisBlocks());
			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator blocking = EventParameter.instance(triggerEvent, EventType.Parameter.ATTACKER);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_BLOCKED);
			pattern.put(EventType.Parameter.ATTACKER, ABILITY_SOURCE_OF_THIS);
			this.addPattern(pattern);
			SetGenerator blockedBy = EventParameter.instance(triggerEvent, EventType.Parameter.DEFENDER);

			SetGenerator thisBlocked = Intersect.instance(Identity.instance(EventType.DECLARE_ONE_BLOCKER), EventTypeOf.instance(triggerEvent));
			SetGenerator choices = IfThenElse.instance(thisBlocked, blocking, blockedBy);

			EventFactory exile = new EventFactory(EventType.EXILE_CHOICE, "You may exile one of those creatures.");
			exile.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exile.parameters.put(EventType.Parameter.PLAYER, You.instance());
			exile.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, 1));
			exile.parameters.put(EventType.Parameter.OBJECT, choices);
			this.addEffect(exile);

			exile.setLink(this);
			this.getLinkManager().addLinkClass(GodsendAbility2.class);
		}
	}

	public static final class GodsendAbility2 extends StaticAbility
	{
		public GodsendAbility2(GameState state)
		{
			super(state, "Opponents can't cast cards with the same name as cards exiled with Godsend.");
			this.getLinkManager().addLinkClass(GodsendAbility1.class);

			SetGenerator exiled = ChosenFor.instance(LinkedTo.instance(This.instance()));
			SetGenerator name = NameOf.instance(exiled);

			SetGenerator opponents = OpponentsOf.instance(You.instance());
			PlayProhibition castSameName = new PlayProhibition(opponents, (c, set) -> set.contains(c.name), name);

			ContinuousEffect.Part cantCast = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			cantCast.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSameName));
			this.addEffectPart(cantCast);
		}
	}

	public Godsend(GameState state)
	{
		super(state);

		// Equipped creature gets +3/+3.
		this.addAbility(new GodsendAbility0(state));

		// Whenever equipped creature blocks or becomes blocked by one or more
		// creatures, you may exile one of those creatures.
		this.addAbility(new GodsendAbility1(state));

		// Opponents can't cast cards with the same name as cards exiled with
		// Godsend.
		this.addAbility(new GodsendAbility2(state));

		// Equip (3)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}
