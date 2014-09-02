package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Defense Grid")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.URZAS_LEGACY, r = Rarity.RARE)})
@ColorIdentity({})
public final class DefenseGrid extends Card
{
	public static final class DefenseGridAbility0 extends StaticAbility
	{
		public DefenseGridAbility0(GameState state)
		{
			super(state, "Each spell costs (3) more to cast except during its controller's turn.");

			SetGenerator activePlayersSpells = ControlledBy.instance(OwnerOf.instance(CurrentTurn.instance()), Stack.instance());
			SetGenerator affectedSpells = RelativeComplement.instance(Spells.instance(), activePlayersSpells);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COST_ADDITION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, affectedSpells);
			part.parameters.put(ContinuousEffectType.Parameter.COST, numberGenerator(3));
			this.addEffectPart(part);
		}
	}

	public DefenseGrid(GameState state)
	{
		super(state);


		// Each spell costs {3} more to cast except during its controller's turn.
		this.addAbility(new DefenseGridAbility0(state));
	}
}
