package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Geth, Lord of the Vault")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("4BB")
@ColorIdentity({Color.BLACK})
public final class GethLordoftheVault extends Card
{
	public static final class GethLordoftheVaultAbility1 extends ActivatedAbility
	{
		public GethLordoftheVaultAbility1(GameState state)
		{
			super(state, "(X)(B): Put target artifact or creature card with converted mana cost X from an opponent's graveyard onto the battlefield under your control tapped. Then that player puts the top X cards of his or her library into his or her graveyard.");
			this.setManaCost(new ManaPool("(X)(B)"));

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.ARTIFACT, Type.CREATURE), HasConvertedManaCost.instance(ValueOfX.instance(This.instance())), InZone.instance(GraveyardOf.instance(OpponentsOf.instance(You.instance())))), "target artifact or creature card with converted mana cost X in an opponent's graveyard"));

			EventFactory factory = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_TAPPED, "Put target artifact or creature card with converted mana cost X from an opponent's graveyard onto the battlefield under your control tapped.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, target);
			this.addEffect(factory);

			this.addEffect(millCards(OwnerOf.instance(target), ValueOfX.instance(This.instance()), "Then that player puts the top X cards of his or her library into his or her graveyard."));
		}
	}

	public GethLordoftheVault(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Intimidate
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Intimidate(state));

		// (X)(B): Put target artifact or creature card with converted mana cost
		// X from an opponent's graveyard onto the battlefield under your
		// control tapped. Then that player puts the top X cards of his or her
		// library into his or her graveyard.
		this.addAbility(new GethLordoftheVaultAbility1(state));
	}
}
