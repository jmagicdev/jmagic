package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Dungeon Geists")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("2UU")
@ColorIdentity({Color.BLUE})
public final class DungeonGeists extends Card
{
	public static final class DungeonGeistsAbility1 extends EventTriggeredAbility
	{
		public DungeonGeistsAbility1(GameState state)
		{
			super(state, "When Dungeon Geists enters the battlefield, tap target creature an opponent controls. That creature doesn't untap during its controller's untap step for as long as you control Dungeon Geists.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "target creature an opponent controls");
			this.addEffect(tap(targetedBy(target), "Tap target creature an opponent controls."));

			EventPattern untapping = new UntapDuringControllersUntapStep(targetedBy(target));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(untapping));

			EventFactory effect = createFloatingEffect(Intersect.instance(ControlledBy.instance(You.instance()), ABILITY_SOURCE_OF_THIS), "That creature doesn't untap during its controller's untap step for as long as you control Dungeon Geists.", part);
			effect.parameters.put(EventType.Parameter.EXPIRES, Not.instance(Intersect.instance(Permanents.instance(), ABILITY_SOURCE_OF_THIS)));
			this.addEffect(effect);
		}
	}

	public DungeonGeists(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Dungeon Geists enters the battlefield, tap target creature an
		// opponent controls. That creature doesn't untap during its
		// controller's untap step for as long as you control Dungeon Geists.
		this.addAbility(new DungeonGeistsAbility1(state));
	}
}
