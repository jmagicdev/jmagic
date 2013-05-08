package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thespian's Stage")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.RARE)})
@ColorIdentity({})
public final class ThespiansStage extends Card
{
	public static final class ThespiansStageAbility1 extends ActivatedAbility
	{
		public ThespiansStageAbility1(GameState state)
		{
			super(state, "(2), (T): Thespian's Stage becomes a copy of target land and gains this ability.");
			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(LandPermanents.instance(), "target land"));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COPY_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			part.parameters.put(ContinuousEffectType.Parameter.ORIGINAL, target);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(new SimpleAbilityFactory(ThespiansStageAbility1.class)));
			this.addEffect(createFloatingEffect(Empty.instance(), "Thespian's Stage becomes a copy of target land and gains this ability.", part));
		}
	}

	public ThespiansStage(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (2), (T): Thespian's Stage becomes a copy of target land and gains
		// this ability.
		this.addAbility(new ThespiansStageAbility1(state));
	}
}
