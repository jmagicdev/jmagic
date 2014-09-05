package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Grafted Exoskeleton")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class GraftedExoskeleton extends Card
{
	public static final class GraftedExoskeletonAbility0 extends StaticAbility
	{
		public GraftedExoskeletonAbility0(GameState state)
		{
			super(state, "Equipped creature gets +2/+2 and has infect.");

			SetGenerator equipped = EquippedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(equipped, +2, +2));

			this.addEffectPart(addAbilityToObject(equipped, org.rnd.jmagic.abilities.keywords.Infect.class));
		}
	}

	public static final class GraftedExoskeletonAbility1 extends EventTriggeredAbility
	{
		public GraftedExoskeletonAbility1(GameState state)
		{
			super(state, "Whenever Grafted Exoskeleton becomes unattached from a permanent, sacrifice that permanent.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.UNATTACH);
			pattern.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			pattern.withResult(Permanents.instance());
			this.addPattern(pattern);

			EventFactory factory = new EventFactory(EventType.SACRIFICE_PERMANENTS, "Sacrifice that permanent.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.PERMANENT, EventResult.instance(TriggerEvent.instance(This.instance())));
			this.addEffect(factory);
		}
	}

	public GraftedExoskeleton(GameState state)
	{
		super(state);

		// Equipped creature gets +2/+2 and has infect. (It deals damage to
		// creatures in the form of -1/-1 counters and to players in the form of
		// poison counters.)
		this.addAbility(new GraftedExoskeletonAbility0(state));

		// Whenever Grafted Exoskeleton becomes unattached from a permanent,
		// sacrifice that permanent.
		this.addAbility(new GraftedExoskeletonAbility1(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
