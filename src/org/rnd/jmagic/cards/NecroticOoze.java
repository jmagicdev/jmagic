package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Necrotic Ooze")
@Types({Type.CREATURE})
@SubTypes({SubType.OOZE})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class NecroticOoze extends Card
{
	public static final class NecroticOozeAbility0 extends StaticAbility
	{
		public NecroticOozeAbility0(GameState state)
		{
			super(state, "As long as Necrotic Ooze is on the battlefield, it has all activated abilities of all creature cards in all graveyards.");

			SetGenerator abilities = ActivatedAbilitiesOf.instance(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(Players.instance()))));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COPY_ABILITIES_TO_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, abilities);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);
		}
	}

	public NecroticOoze(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// As long as Necrotic Ooze is on the battlefield, it has all activated
		// abilities of all creature cards in all graveyards.
		this.addAbility(new NecroticOozeAbility0(state));
	}
}
