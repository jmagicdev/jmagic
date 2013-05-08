package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tolarian Academy")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class TolarianAcademy extends Card
{
	public static final class TolarianAcademyMana extends ActivatedAbility
	{
		public TolarianAcademyMana(GameState state)
		{
			super(state, "(T): Add (U) to your mana pool for each artifact you control.");

			this.costsTap = true;

			SetGenerator numControllersArtifacts = Count.instance(Intersect.instance(HasType.instance(Type.ARTIFACT), ControlledBy.instance(You.instance())));

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			parameters.put(EventType.Parameter.MANA, Identity.instance(new ManaPool("U")));
			parameters.put(EventType.Parameter.NUMBER, numControllersArtifacts);
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(new EventFactory(EventType.ADD_MANA, parameters, "Add (U) to your mana pool for each artifact you control."));
		}
	}

	public TolarianAcademy(GameState state)
	{
		super(state);

		this.addAbility(new TolarianAcademyMana(state));
	}
}
