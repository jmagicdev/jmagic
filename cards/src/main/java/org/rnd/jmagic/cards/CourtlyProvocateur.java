package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Courtly Provocateur")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class CourtlyProvocateur extends Card
{
	public static final class CourtlyProvocateurAbility0 extends ActivatedAbility
	{
		public CourtlyProvocateurAbility0(GameState state)
		{
			super(state, "(T): Target creature attacks this turn if able.");
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, target);

			this.addEffect(createFloatingEffect("Target creature attacks this turn if able.", part));
		}
	}

	public static final class CourtlyProvocateurAbility1 extends ActivatedAbility
	{
		public CourtlyProvocateurAbility1(GameState state)
		{
			super(state, "(T): Target creature blocks this turn if able.");
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.DEFENDING, target);

			this.addEffect(createFloatingEffect("Target creature blocks this turn if able.", part));
		}
	}

	public CourtlyProvocateur(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Target creature attacks this turn if able.
		this.addAbility(new CourtlyProvocateurAbility0(state));

		// (T): Target creature blocks this turn if able.
		this.addAbility(new CourtlyProvocateurAbility1(state));
	}
}
