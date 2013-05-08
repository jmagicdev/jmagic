package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tyrant of Discord")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4RRR")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class TyrantofDiscord extends Card
{
	public static final class WasNonland extends SetGenerator
	{
		public static SetGenerator instance(SetGenerator zoneChanges)
		{
			return new WasNonland(zoneChanges);
		}

		private final SetGenerator zoneChanges;

		private WasNonland(SetGenerator zoneChanges)
		{
			this.zoneChanges = zoneChanges;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Set zcs = this.zoneChanges.evaluate(state, thisObject);
			if(zcs.isEmpty())
				return Empty.set;

			for(ZoneChange z: zcs.getAll(ZoneChange.class))
				if(state.<GameObject>get(z.oldObjectID).getTypes().contains(Type.LAND))
					return Empty.set;
			return NonEmpty.set;
		}
	}

	public static final class TyrantofDiscordAbility0 extends EventTriggeredAbility
	{
		public TyrantofDiscordAbility0(GameState state)
		{
			super(state, "When Tyrant of Discord enters the battlefield, target opponent chooses a permanent he or she controls at random and sacrifices it. If a nonland permanent is sacrificed this way, repeat this process.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));

			EventFactory random = new EventFactory(RANDOM, "Target opponent chooses a permanent he or she controls at random");
			random.parameters.put(EventType.Parameter.OBJECT, ControlledBy.instance(target));

			EventFactory sacrifice = new EventFactory(EventType.SACRIFICE_PERMANENTS, "and sacrifices it");
			sacrifice.parameters.put(EventType.Parameter.CAUSE, This.instance());
			sacrifice.parameters.put(EventType.Parameter.PLAYER, target);
			sacrifice.parameters.put(EventType.Parameter.PERMANENT, EffectResult.instance(random));

			SetGenerator nonlandSacrificed = WasNonland.instance(EffectResult.instance(sacrifice));

			EventFactory repeat = new EventFactory(REPEAT_THIS_PROCESS, "Target opponent chooses a permanent he or she controls at random and sacrifices it. If a nonland permanent is sacrificed this way, repeat this process.");
			repeat.parameters.put(EventType.Parameter.EVENT, Identity.instance(sequence(random, sacrifice)));
			repeat.parameters.put(EventType.Parameter.EFFECT, Identity.instance(Not.instance(nonlandSacrificed)));
			this.addEffect(repeat);
		}
	}

	public TyrantofDiscord(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// When Tyrant of Discord enters the battlefield, target opponent
		// chooses a permanent he or she controls at random and sacrifices it.
		// If a nonland permanent is sacrificed this way, repeat this process.
		this.addAbility(new TyrantofDiscordAbility0(state));
	}
}
