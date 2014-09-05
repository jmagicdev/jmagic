package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Semblance Anvil")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class SemblanceAnvil extends Card
{
	public static final class SemblanceAnvilAbility0 extends EventTriggeredAbility
	{
		public SemblanceAnvilAbility0(GameState state)
		{
			super(state, "When Semblance Anvil enters the battlefield, you may exile a nonland card from your hand.");
			this.addPattern(whenThisEntersTheBattlefield());
			EventFactory factory = exile(You.instance(), RelativeComplement.instance(InZone.instance(HandOf.instance(You.instance())), HasType.instance(Type.LAND)), 1, "Exile a nonland card from your hand.");
			factory.setLink(this);
			this.addEffect(youMay(factory, "You may exile a nonland card from your hand."));

			this.getLinkManager().addLinkClass(SemblanceAnvilAbility1.class);
		}
	}

	public static final class SemblanceAnvilAbility1 extends StaticAbility
	{
		public SemblanceAnvilAbility1(GameState state)
		{
			super(state, "Spells you cast that share a card type with the exiled card cost (2) less to cast.");

			SetGenerator exiled = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));
			SetGenerator hasTypes = HasType.instance(TypesOf.instance(exiled));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(hasTypes, Spells.instance(), ControlledBy.instance(You.instance(), Stack.instance())));
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("(2)")));
			this.addEffectPart(part);

			this.getLinkManager().addLinkClass(SemblanceAnvilAbility0.class);
		}
	}

	public SemblanceAnvil(GameState state)
	{
		super(state);

		// Imprint \u2014 When Semblance Anvil enters the battlefield, you may
		// exile a nonland card from your hand.
		this.addAbility(new SemblanceAnvilAbility0(state));

		// Spells you cast that share a card type with the exiled card cost (2)
		// less to cast.
		this.addAbility(new SemblanceAnvilAbility1(state));
	}
}
