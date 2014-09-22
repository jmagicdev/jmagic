package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ragemonger")
@Types({Type.CREATURE})
@SubTypes({SubType.MINOTAUR, SubType.SHAMAN})
@ManaCost("1BR")
@ColorIdentity({Color.BLACK, Color.RED})
public final class Ragemonger extends Card
{
	public static final class RagemongerAbility0 extends StaticAbility
	{
		public RagemongerAbility0(GameState state)
		{
			super(state, "Minotaur spells you cast cost (B)(R) less to cast. This effect reduces only the amount of colored mana you pay.");

			SetGenerator minotaurs = HasSubType.instance(SubType.MINOTAUR);
			SetGenerator yourMinotaurSpells = Intersect.instance(ControlledBy.instance(You.instance(), Stack.instance()), minotaurs);

			ContinuousEffect.Part reduction = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			reduction.parameters.put(ContinuousEffectType.Parameter.OBJECT, yourMinotaurSpells);
			reduction.parameters.put(ContinuousEffectType.Parameter.COLOR, Empty.instance());
			reduction.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("BR")));
			this.addEffectPart(reduction);
		}
	}

	public Ragemonger(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Minotaur spells you cast cost (B)(R) less to cast. This effect
		// reduces only the amount of colored mana you pay. (For example, if you
		// cast a Minotaur spell with mana cost (2)(R), it costs (2) to cast.)
		this.addAbility(new RagemongerAbility0(state));
	}
}
