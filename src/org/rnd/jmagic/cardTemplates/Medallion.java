package org.rnd.jmagic.cardTemplates;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class Medallion extends Card
{
	public static final class ColorReduction extends StaticAbility
	{
		private Color color;

		public ColorReduction(GameState state, Color color)
		{
			super(state, org.rnd.util.CamelCase.convertWord(color.toString()) + " spells cost you (1) less to cast.");
			this.color = color;

			SetGenerator spellsYouCast = Intersect.instance(Spells.instance(), ControlledBy.instance(You.instance(), Stack.instance()));
			SetGenerator stuff = Intersect.instance(HasColor.instance(color), spellsYouCast);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, stuff);
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(new ManaPool("1")));

			this.addEffectPart(part);
		}

		@Override
		public ColorReduction create(Game game)
		{
			return new ColorReduction(game.physicalState, this.color);
		}
	}

	public Medallion(GameState state, Color color)
	{
		super(state);

		// [color] spells you cast cost (1) less to cast.
		this.addAbility(new ColorReduction(state, color));
	}
}
